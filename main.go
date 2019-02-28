package main

import (
	"github.com/labstack/echo"
	"github.com/labstack/echo/middleware"
	"github.com/mikrzem/gwynder-schedules/database"
	"github.com/mikrzem/gwynder-schedules/events"
	"net/http"
)

func main() {
	database.InitializeDatabase()

	e := echo.New()

	e.Use(middleware.Logger())
	e.Use(middleware.Recover())

	e.GET("/", func(c echo.Context) error {
		return c.String(http.StatusOK, "Hello, World!")
	})

	groupFactory := func(path string) *echo.Group {
		return e.Group("/api/schedules" + path)
	}

	events.Router(groupFactory)

	e.Logger.Fatal(e.Start(":1323"))
}
