package events

import (
	"github.com/labstack/echo"
	"github.com/mikrzem/gwynder-schedules/common"
)

func Router(factory common.GroupFactory) {
	group := factory("/events")
	group.GET("/", func(context echo.Context) error {
		return nil
	})
}
