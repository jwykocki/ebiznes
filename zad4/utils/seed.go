// utils/seed.go
package utils

import "crud-echo-gorm/models"

func SeedDB() {

	categories := []models.Category{
		{Name: "Elektronika"},
		{Name: "Ciuchy"},
	}

	for _, category := range categories {
		models.DB.FirstOrCreate(&category, models.Category{Name: category.Name})
	}

	products := []models.Product{
		{
			Name:        "Laptop",
			Description: "1TB SSD",
			Price:       4999.99,
			CategoryID:  1,
		},
		{
			Name:        "Smartfon",
			Description: "128GB",
			Price:       2499.99,
			CategoryID:  1,
		},
	}

	for _, product := range products {
		models.DB.FirstOrCreate(&product, models.Product{Name: product.Name})
	}
}
