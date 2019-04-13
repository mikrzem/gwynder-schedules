package central

import (
	"bytes"
	"encoding/json"
	"errors"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"time"
)

var registerActive = os.Getenv("schedules_register") == "true"
var registerUrl = os.Getenv("schedules_register_url")
var selfUrl = os.Getenv("schedules_self_url")
var tokenLocation = os.Getenv("schedules_token_file")

type ApplicationConfiguration struct {
	Active      bool   `json:"active"`
	Path        string `json:"path"`
	DisplayName string `json:"displayName"`
	StartPath   string `json:"startPath"`
}

type DashboardConfiguration struct {
	Active bool    `json:"active"`
	Path   *string `json:"path"`
}

type HealthConfiguration struct {
	Active bool    `json:"active"`
	Path   *string `json:"path"`
}

type ApiConfiguration struct {
	Name        string                   `json:"name"`
	Path        string                   `json:"path"`
	Application ApplicationConfiguration `json:"application"`
	Dashboard   DashboardConfiguration   `json:"dashboard"`
	Health      HealthConfiguration      `json:"health"`
}

var configuration = ApiConfiguration{
	Name: "schedules",
	Path: selfUrl + "/api/schedules",
	Application: ApplicationConfiguration{
		Active:      true,
		Path:        selfUrl + "/application/schedules",
		DisplayName: "Schedules",
		StartPath:   "page/",
	},
	Dashboard: DashboardConfiguration{
		Active: true,
	},
	Health: HealthConfiguration{
		Active: true,
	},
}

func Register() {
	if registerActive {
		log.Println("ENABLED Registering service with central")
		registerWithRetry()
	} else {
		log.Println("DISABLED Registering service with central")
	}
}

func registerWithRetry() {
	attempt := 0
	success := false
	for attempt < 10 && !success {
		success = tryRegister()
		attempt++
		if !success {
			time.Sleep(1 * time.Minute)
		}
	}
	if success {
		log.Println("Successfully registered service with central")
	} else {
		log.Printf("Failed to register service after %d attempts", attempt)
		panic(errors.New("failed to register service with central"))
	}
}

func tryRegister() bool {
	content, _ := json.Marshal(configuration)
	request, _ := http.NewRequest("PUT", registerUrl+"/proxy/api/schedules", bytes.NewBuffer(content))
	response, err := executeRequest(request)
	return checkResponse(response, err)
}

func executeRequest(request *http.Request) (*http.Response, error) {
	request.Header.Set("Content-Type", "application/json")
	request.Header.Set("InternalToken", readToken())
	client := &http.Client{}
	response, err := client.Do(request)
	return response, err
}

func readToken() string {
	content, err := ioutil.ReadFile(tokenLocation)
	if err != nil {
		log.Println(err)
		return ""
	}
	return string(content)
}

func checkResponse(response *http.Response, err error) bool {
	log.Printf("Attempting to register api")
	if err != nil {
		log.Printf("Failed to register api")
		log.Println(err)
		return false
	} else if response.StatusCode != 200 {
		log.Printf("Server responded: %d", response.StatusCode)
		return false
	} else {
		log.Printf("Correctly registered api")
		return true
	}
}
