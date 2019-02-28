package database

import (
	"github.com/jinzhu/gorm"
	"github.com/mikrzem/gwynder-schedules/events"
)

func ApplyMigration(db *gorm.DB) {

	db.AutoMigrate(&events.ScheduledEventEntity{})

}
