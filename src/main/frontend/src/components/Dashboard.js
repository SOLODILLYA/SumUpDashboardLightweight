import React, { useEffect, useState } from "react";
import axios from "axios";
import { Line, Pie } from "react-chartjs-2";
import { format, parseISO } from "date-fns";
import "chart.js/auto";
import "./Dashboard.css";

function Dashboard() {
  const [summary, setSummary] = useState(null);
  const [loading, setLoading] = useState(true);
  const [visibleCount, setVisibleCount] = useState(10);
  const [visibleItemAndComboCount, setVisibleItemAndComboCount] = useState(10);
  const [grouping, setGrouping] = useState("1h");
  let groupedData = [];

  if (!loading && summary?.salesOverTime) {
    groupedData = groupSales(summary.salesOverTime, grouping);
  }

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
        <div className="grouping-buttons">
          <button
            onClick={() => setGrouping("30m")}
            className={grouping === "30m" ? "active" : ""}
          >
            30 min
          </button>
          <button
            onClick={() => setGrouping("1h")}
            className={grouping === "1h" ? "active" : ""}
          >
            1 hour
          </button>
          <button
            onClick={() => setGrouping("1d")}
            className={grouping === "1d" ? "active" : ""}
          >
            1 day
          </button>
        </div>
        <Line
          data={{
            labels: groupedData.map((pt) => pt.date),
            datasets: [
              {
                label: "Sales (€)",
                data: groupedData.map((pt) => pt.amount),
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
                  color: "#f5f6fa",
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
      <div className="charts-grid">
        <div className="chart-block">
          <h2>Most Sold Items</h2>
          <Pie
            data={{
              labels: summary.mostSoldProducts.map((p) => p.name),
              datasets: [
                {
                  label: "Products Sold",
                  data: summary.mostSoldProducts.map((p) => p.quantity),
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
              rotation: Math.PI,
              //circumference: 2 * Math.PI,
              cutout: "60%",
              plugins: {
                legend: {
                  // display: false,
                  labels: {
                    color: "#fff",
                  },
                },
              },
            }}
          />
        </div>

        <div className="chart-block">
          <h2>Most Sold Combos</h2>
          <Pie
            data={{
              labels: Object.keys(summary.mostSoldCombos),
              datasets: [
                {
                  data: Object.values(summary.mostSoldCombos),
                  backgroundColor: [
                    "#6c5ce7",
                    "#636e72",
                    "#00cec9",
                    "#ffeaa7",
                    "#e17055",
                    "#fd79a8",
                    "#fab1a0",
                    "#0984e3",
                    "#2d3436",
                    "#8e44ad",
                  ],
                  borderWidth: 1,
                },
              ],
            }}
            options={{
              plugins: {
                // legend: { display: false },
                legend: {
                  labels: {
                    color: "#fff",
                  },
                },
              },
              cutout: "60%",
            }}
          />
        </div>
        <div className="chart-block">
          <h2>Least Sold Items</h2>
          <Pie
            data={{
              labels: summary.leastSoldProducts.map((p) => p.name),
              datasets: [
                {
                  label: "Products Sold",
                  data: summary.leastSoldProducts.map((p) => p.quantity),
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
              rotation: Math.PI,
              //circumference: 2 * Math.PI,
              cutout: "60%",
              plugins: {
                legend: {
                  // display: false,
                  labels: {
                    color: "#fff",
                  },
                },
              },
            }}
          />
        </div>

        <div className="chart-block">
          <h2>Least Sold Combos</h2>
          <Pie
            data={{
              labels: Object.keys(summary.leastSoldCombos),
              datasets: [
                {
                  data: Object.values(summary.leastSoldCombos),
                  backgroundColor: [
                    "#6c5ce7",
                    "#636e72",
                    "#00cec9",
                    "#ffeaa7",
                    "#e17055",
                    "#fd79a8",
                    "#fab1a0",
                    "#0984e3",
                    "#2d3436",
                    "#8e44ad",
                  ],
                  borderWidth: 1,
                },
              ],
            }}
            options={{
              plugins: {
                // legend: { display: false },
                legend: {
                  labels: {
                    color: "#fff",
                  },
                },
              },
              cutout: "60%",
            }}
          />
        </div>
      </div>
      <div className="dual-table-wrapper">
        {/* Most Sold Items Table */}
        <div className="table-block">
          <h2>Most Sold Items</h2>
          <table className="data-table">
            <thead>
              <tr>
                <th>Item</th>
                <th>Quantity</th>
              </tr>
            </thead>
            <tbody>
              {summary.soldProducts
                .slice(0, visibleItemAndComboCount)
                .map((item, index) => (
                  <tr key={index}>
                    <td>{item.name}</td>
                    <td>{item.quantity}</td>
                  </tr>
                ))}
            </tbody>
          </table>
        </div>

        {/* Most Sold Combos Table */}
        <div className="table-block">
          <h2>Most Sold Combos</h2>
          <table className="data-table">
            <thead>
              <tr>
                <th>Combo</th>
                <th>Count</th>
              </tr>
            </thead>
            <tbody>
              {Object.entries(summary.soldCombos)
                .slice(0, visibleItemAndComboCount)
                .map(([combo, count], index) => (
                  <tr key={index}>
                    <td>{combo}</td>
                    <td>{count}</td>
                  </tr>
                ))}
            </tbody>
          </table>
        </div>
      </div>

      {(visibleItemAndComboCount < summary.soldProducts.length ||
        visibleItemAndComboCount < Object.keys(summary.soldCombos).length) && (
        <div className="load-more-container">
          <button
            onClick={() => setVisibleItemAndComboCount((c) => c + 10)}
            className="load-more-button"
          >
            Load More
          </button>
        </div>
      )}

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
            {summary.transactions.slice(0, visibleCount).map((tx) => (
              <tr key={tx.id}>
                <td>{tx.date}</td>
                <td>{tx.amount}</td>
                <td>{tx.paymentType}</td>
                <td>{tx.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
        {visibleCount < summary.transactions.length && (
          <div className="load-more-container">
            <button
              onClick={() => setVisibleCount((prev) => prev + 10)}
              className="load-more-button"
            >
              Load More
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
function groupSales(sales, interval) {
  const buckets = {};

  sales.forEach((pt) => {
    const date = parseISO(pt.date);
    let bucketKey;

    if (interval === "30m") {
      const hour = date.getHours();
      const half = date.getMinutes() < 30 ? "00" : "30";
      bucketKey = `${format(date, "yyyy-MM-dd")} ${hour
        .toString()
        .padStart(2, "0")}:${half}`;
    } else if (interval === "1h") {
      bucketKey = `${format(date, "yyyy-MM-dd")} ${date
        .getHours()
        .toString()
        .padStart(2, "0")}:00`;
    } else {
      bucketKey = format(date, "yyyy-MM-dd");
    }

    if (!buckets[bucketKey]) {
      buckets[bucketKey] = 0;
    }
    buckets[bucketKey] += pt.amount;
  });

  return Object.entries(buckets)
    .sort(([a], [b]) => a.localeCompare(b))
    .map(([key, amount]) => ({ date: key, amount }));
}

export default Dashboard;
