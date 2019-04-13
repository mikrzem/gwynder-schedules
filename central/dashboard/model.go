package dashboard

type DashboardInfoRow struct {
	Description string `json:"description"`
	Content     string `json:"content"`
}

type DashboardInfo struct {
	Title string             `json:"title"`
	Rows  []DashboardInfoRow `json:"rows"`
}
