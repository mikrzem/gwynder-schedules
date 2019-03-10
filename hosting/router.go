package hosting

import (
	"github.com/labstack/echo"
	"net/http"
	"os"
	"path"
)

var applicationDirectory = os.Getenv("schedules_application_directory")

func Router(app *echo.Echo) {
	app.Static("/application/schedules/resources", applicationDirectory)
	app.GET("/application/schedules/page/*", func(context echo.Context) error {
		return context.File(path.Join(applicationDirectory, "index.html"))
	})
	app.GET("/application/schedules/page", func(context echo.Context) error {
		return context.Redirect(http.StatusFound, "/application/schedules/page/")
	})
}
