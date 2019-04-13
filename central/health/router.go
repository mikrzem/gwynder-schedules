package health

import (
	"github.com/labstack/echo"
	"github.com/mikrzem/gwynder-schedules/common"
	"net/http"
	"time"
)

var startUpTime = time.Now().Format(time.RFC3339)

func Router(factory common.GroupFactory) {
	group := factory("/central/health")
	group.GET("", func(context echo.Context) error {
		return context.JSON(
			http.StatusOK,
			HealthInfo{
				Healthy:     true,
				StartupTime: startUpTime,
			},
		)
	})
}
