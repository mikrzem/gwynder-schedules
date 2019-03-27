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
var apiConfiguration = map[string]string{
	"name": "schedules",
	"path": selfUrl + "/api/schedules",
}
var applicationConfiguration = map[string]string{
	"name":        "schedules",
	"path":        selfUrl + "/application/schedules",
	"displayName": "Schedules",
	"startPath":   "page/",
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
	apiRegistered := tryRegisterApi()
	applicationRegistered := tryRegisterApplication()
	return apiRegistered && applicationRegistered
}

func tryRegisterApi() bool {
	content, _ := json.Marshal(apiConfiguration)
	request, _ := http.NewRequest("PUT", registerUrl+"/proxy/api/schedules", bytes.NewBuffer(content))
	response, err := executeRequest(request)
	return checkResponse(response, err, "api")
}

func tryRegisterApplication() bool {
	content, _ := json.Marshal(applicationConfiguration)
	request, _ := http.NewRequest("PUT", registerUrl+"/proxy/application/schedules", bytes.NewBuffer(content))
	response, err := executeRequest(request)
	return checkResponse(response, err, "application")
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

func checkResponse(response *http.Response, err error, requestType string) bool {
	log.Printf("Attempting to register %s", requestType)
	if err != nil {
		log.Printf("Failed to register %s", requestType)
		log.Println(err)
		return false
	} else if response.StatusCode != 200 {
		log.Printf("Server responded: %d to %s", response.StatusCode, requestType)
		return false
	} else {
		log.Printf("Correctly registered %s", requestType)
		return true
	}
}
