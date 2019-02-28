package common

import "github.com/jinzhu/gorm"

type BaseData struct {
	ID uint
}

type OwnedModel struct {
	gorm.Model
	Owner string
}
