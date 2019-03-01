package events

import (
	"errors"
	"fmt"
	"github.com/mikrzem/gwynder-schedules/database"
	"time"
)

func Select(owner string, fromTime time.Time, toTime time.Time) []ScheduledEventEntity {
	var events []ScheduledEventEntity
	database.Database.
		Where("owner = ? AND start_time <= ? AND start_time >= ?", owner, fromTime, toTime).
		Order("start_time").
		Find(&events)
	return events
}

func Get(owner string, id uint) ScheduledEventEntity {
	var event ScheduledEventEntity
	database.Database.
		Where("owner = ? AND id = ?", owner, id).
		First(&event)
	if database.Database.NewRecord(event) {
		panic(errors.New("No event found with id " + fmt.Sprint(id)))
	}
	return event
}

func Create(owner string, event ScheduledEventData) ScheduledEventEntity {
	result := ScheduledEventEntity{}
	result.FillData(event.ScheduledEvent)
	result.Owner = owner
	database.Database.Create(&result)
	return result
}

func Update(owner string, id uint, event ScheduledEventData) ScheduledEventEntity {
	result := Get(owner, id)
	result.FillData(event.ScheduledEvent)
	database.Database.Update(&result)
	return result
}

func Delete(owner string, id uint) {
	result := Get(owner, id)
	database.Database.Delete(result)
}
