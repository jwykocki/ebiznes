package models

import "gorm.io/gorm"

type Product struct {
	gorm.Model
	Name        string   `json:"name" gorm:"not null"`
	Description string   `json:"description"`
	Price       float64  `json:"price" gorm:"not null"`
	CategoryID  uint     `json:"category_id"`
	Category    Category `json:"category" gorm:"foreignKey:CategoryID"`
}
