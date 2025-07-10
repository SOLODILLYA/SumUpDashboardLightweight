import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Line } from 'react-chartjs-2';
import 'chart.js/auto';
import './Dashboard.css'; // make sure this file exists

function Dashboard() {
  const [summary, setSummary] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get('http://localhost:8080/api/summary')
      .then(res => {
        console.log("API Response:", res.data);
        setSummary(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error("Error fetching summary:", err);
        setLoading(false);
      });
  }, []);

  if (loading) return <p className="loading">Loading dashboard...</p>;

  return (
    <div className="dashboard-container">
      <h1 className="dashboard-title">SumUp Dashboard</h1>

      <div className="summary-cards">
        <div><strong>Total Sales:</strong> €{summary.totalSales}</div>
        <div><strong>Transactions:</strong> {summary.numTransactions}</div>
        <div><strong>Average:</strong> €{summary.avgTransactionValue}</div>
      </div>

      <div className="chart-section">
        <h2>Sales Over Time</h2>
        <Line
            data={{
                labels: summary.salesOverTime.map(pt => pt.date),
                datasets: [{
                label: 'Daily Sales (€)',
                data: summary.salesOverTime.map(pt => pt.amount),
                borderColor: '#9b59b6',         // vibrant purple
                backgroundColor: '#9b59b622', 
                fill: true,
                tension: 0.3,
                pointRadius: 4,
                pointHoverRadius: 6
                }]
            }}
            options={{
                responsive: true,
                plugins: {
                legend: {
                    labels: {
                    color: '#00d1ff' // legend label color
                    }
                }
                },
                scales: {
                x: {
                    ticks: { color: '#aaa' },
                    grid: { color: '#333' }
                },
                y: {
                    ticks: { color: '#aaa' },
                    grid: { color: '#333' }
                }
                }
            }}
            />

      </div>

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
            {summary.transactions.map(tx => (
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
