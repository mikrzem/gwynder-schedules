package migration

import (
	"github.com/jinzhu/gorm"
	"github.com/mikrzem/gwynder-schedules/events"
)

func ApplyMigration(db *gorm.DB) {

	db.SingularTable(true)
	db.AutoMigrate(&events.ScheduledEventEntity{})

}
