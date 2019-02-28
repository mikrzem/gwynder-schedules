package events

import (
	"github.com/mikrzem/gwynder-schedules/common"
	"time"
)

type ScheduledEvent struct {
	StartTime time.Time
	EndTime   time.Time
	Title     string
}

type ScheduledEventEntity struct {
	common.OwnedModel
	ScheduledEvent
}

type ScheduledEventData struct {
	common.BaseData
	ScheduledEvent
}

func (event ScheduledEvent) FillData(source ScheduledEvent) {
	event.StartTime = source.StartTime
	event.EndTime = source.EndTime
	event.Title = source.Title
}

func (event ScheduledEvent) Clone() ScheduledEvent {
	return ScheduledEvent{
		StartTime: event.StartTime,
		EndTime:   event.EndTime,
		Title:     event.Title,
	}
}

func (entity ScheduledEventEntity) ToData() ScheduledEventData {
	return ScheduledEventData{
		common.BaseData{ID: entity.ID},
		entity.Clone(),
	}
}
