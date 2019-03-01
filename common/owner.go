package common

import (
	"github.com/labstack/echo"
	"log"
	"os"
)

func Owner(context echo.Context) string {
	owner := context.Request().Header.Get("CentralUserDisplayName")
	if owner == "" {
		if os.Getenv("schedules_disable_default_user") == "true" {
			log.Fatal("Missing user")
		} else {
			log.Println("Using default user")
			return "default_owner"
		}
	}
	return owner
}
