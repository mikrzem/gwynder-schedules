package main

import (
	"github.com/labstack/echo"
	"github.com/labstack/echo/middleware"
	"github.com/mikrzem/gwynder-schedules/central"
	"github.com/mikrzem/gwynder-schedules/central/dashboard"
	"github.com/mikrzem/gwynder-schedules/central/health"
	"github.com/mikrzem/gwynder-schedules/database"
	"github.com/mikrzem/gwynder-schedules/database/migration"
	"github.com/mikrzem/gwynder-schedules/events"
	"github.com/mikrzem/gwynder-schedules/hosting"
	"log"
	"net/http"
)

func main() {
	initialize()

	app := echo.New()

	app.Use(middleware.Logger())
	app.Use(middleware.Recover())
	app.Use(middleware.CORS())

	app.GET("/", func(c echo.Context) error {
		return c.String(http.StatusOK, "Hello, World!")
	})

	groupFactory := func(path string) *echo.Group {
		return app.Group("/api/schedules" + path)
	}

	hosting.Router(app)

	events.Router(groupFactory)
	health.Router(groupFactory)
	dashboard.Router(groupFactory)

	app.Logger.Fatal(app.Start(":1323"))
}

func initialize() {
	database.InitializeDatabase()
	log.Println("Connected to database")
	migration.ApplyMigration(database.Database)
	log.Println("Applied database migration")
	central.Register()
}
