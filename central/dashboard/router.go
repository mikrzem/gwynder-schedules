package dashboard

import (
	"github.com/labstack/echo"
	"github.com/mikrzem/gwynder-schedules/common"
	"github.com/mikrzem/gwynder-schedules/events"
	"net/http"
	"strconv"
)

func Router(factory common.GroupFactory) {
	group := factory("/central/dashboard")
	group.GET("", func(context echo.Context) error {
		return context.JSON(
			http.StatusOK,
			createDashboard(common.Owner(context)),
		)
	})
}

func createDashboard(owner string) DashboardInfo {
	return DashboardInfo{
		Title: "Schedules",
		Rows: []DashboardInfoRow{
			eventCount(owner),
		},
	}
}

func eventCount(owner string) DashboardInfoRow {
	return DashboardInfoRow{
		Description: "Count",
		Content:     strconv.FormatUint(events.Count(owner), 10),
	}
}
