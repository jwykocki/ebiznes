package routes

import (
	"crud-echo-gorm/controllers"

	"github.com/labstack/echo/v4"
)

func InitRoutes() *echo.Echo {
	e := echo.New()

	api := e.Group("/api")
	{
		products := api.Group("/products")
		{
			products.GET("", controllers.GetProducts)
			products.POST("", controllers.CreateProduct)
			products.GET("/:id", controllers.GetProduct)
			products.PUT("/:id", controllers.UpdateProduct)
			products.DELETE("/:id", controllers.DeleteProduct)
		}
	}

	return e
}
