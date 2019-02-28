package common

import "github.com/labstack/echo"

type GroupFactory func(path string) *echo.Group
