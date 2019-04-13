package health

type HealthInfo struct {
	Healthy     bool   `json:"healthy"`
	StartupTime string `json:"startupTime"`
}
