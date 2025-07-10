import React, { useEffect, useState } from "react";
import axios from "axios";
import { Line, Pie } from "react-chartjs-2";
import "chart.js/auto";
import "./Dashboard.css";

function Dashboard() {
  const [summary, setSummary] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/summary")
      .then((res) => {
        console.log("API Response:", res.data);
        setSummary(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error fetching summary:", err);
        setLoading(false);
      });
  }, []);

  if (loading) return <p className="loading">Loading dashboard...</p>;

  return (
    <div className="dashboard-container">
      <h1 className="dashboard-title">SumUp Dashboard</h1>

      <div className="summary-cards">
        <div>
          <strong>Total Sales:</strong> €{summary.totalSales}
        </div>
        <div>
          <strong>Transactions:</strong> {summary.numTransactions}
        </div>
        <div>
          <strong>Average:</strong> €{summary.avgTransactionValue}
        </div>
      </div>

      <div className="chart-section">
        <h2>Sales Over Time</h2>
        <Line
          data={{
            labels: summary.salesOverTime.map((pt) => pt.date),
            datasets: [
              {
                label: "Daily Sales (€)",
                data: summary.salesOverTime.map((pt) => pt.amount),
                borderColor: "#9b59b6",
                backgroundColor: "#9b59b622",
                fill: true,
                tension: 0.3,
                pointRadius: 4,
                pointHoverRadius: 6,
              },
            ],
          }}
          options={{
            responsive: true,
            plugins: {
              legend: {
                labels: {
                  color: "#00d1ff",
                },
              },
            },
            scales: {
              x: {
                ticks: { color: "#aaa" },
                grid: { color: "#333" },
              },
              y: {
                ticks: { color: "#aaa" },
                grid: { color: "#333" },
              },
            },
          }}
        />
      </div>
      <Pie
        data={{
          labels: summary.productSales.map((p) => p.name),
          datasets: [
            {
              label: "Products Sold",
              data: summary.productSales.map((p) => p.quantity),
              backgroundColor: [
                "#a29bfe", // lavender
                "#6c5ce7", // soft indigo
                "#74b9ff", // light blue
                "#00b894", // emerald green
                "#55efc4", // mint
                "#ffeaa7", // pastel yellow (warm accent)
                "#fab1a0", // soft coral
                "#fd79a8", // clean pink
                "#e17055", // soft orange
                "#636e72", // neutral gray
              ],
              borderWidth: 1,
            },
          ],
        }}
        options={{
          cutout: "50%",
          plugins: {
            legend: {
              display: false,
              labels: {
                color: "#fff",
              },
            },
          },
        }}
      />

      <div className="transactions-section">
        <h2>Recent Transactions</h2>
        <table className="transactions-table">
          <thead>
            <tr>
              <th>Date</th>
              <th>Amount (€)</th>
              <th>Payment Type</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {summary.transactions.map((tx) => (
              <tr key={tx.id}>
                <td>{tx.date}</td>
                <td>{tx.amount}</td>
                <td>{tx.paymentType}</td>
                <td>{tx.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Dashboard;
