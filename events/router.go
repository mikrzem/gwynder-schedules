package events

import (
	"github.com/labstack/echo"
	"github.com/mikrzem/gwynder-schedules/common"
	"log"
	"net/http"
	"strconv"
	"time"
)

func Router(factory common.GroupFactory) {
	group := factory("/events")
	group.GET("/", func(context echo.Context) error {
		return context.JSON(
			http.StatusOK,
			convertToData(
				Select(
					common.Owner(context),
					findFromTime(context),
					findToTime(context),
				),
			),
		)
	})
	group.GET("/:id", func(context echo.Context) error {
		id := findId(context)
		return context.JSON(
			http.StatusOK,
			Get(
				common.Owner(context),
				uint(id),
			),
		)
	})
	group.POST("/", func(context echo.Context) error {
		event := findBody(context)
		return context.JSON(
			http.StatusOK,
			Create(
				common.Owner(context),
				event,
			),
		)
	})
	group.PUT("/:id", func(context echo.Context) error {
		id := findId(context)
		event := findBody(context)
		return context.JSON(
			http.StatusOK,
			Update(
				common.Owner(context),
				id,
				event,
			),
		)
	})
	group.DELETE("/:id", func(context echo.Context) error {
		id := findId(context)
		Delete(
			common.Owner(context),
			id,
		)
		return context.String(http.StatusOK, "OK")
	})
}

func findFromTime(context echo.Context) time.Time {
	return findTime(context, "from", time.Now())
}

func findToTime(context echo.Context) time.Time {
	return findTime(context, "to", time.Now().AddDate(0, 0, 1))
}

func findTime(context echo.Context, param string, defaultTime time.Time) time.Time {
	text := context.QueryParam(param)
	if text == "" {
		return defaultTime
	} else {
		result, err := time.Parse(time.RFC3339, text)
		if err != nil {
			log.Printf("Failed to parse date: %s\n", text)
			log.Println(err)
			return defaultTime
		} else {
			return result
		}
	}
}

func convertToData(entities []ScheduledEventEntity) []ScheduledEventData {
	result := []ScheduledEventData{}
	for _, entity := range entities {
		result = append(result, entity.ToData())
	}
	return result
}

func findId(context echo.Context) uint {
	id, err := strconv.ParseUint(context.Param("id"), 10, 32)
	if err != nil {
		panic(err)
	}
	return uint(id)
}

func findBody(context echo.Context) ScheduledEventData {
	var event ScheduledEventData
	if err := context.Bind(&event); err != nil {
		panic(err)
	}
	return event
}
