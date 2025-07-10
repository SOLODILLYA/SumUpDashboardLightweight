// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import { Line } from 'react-chartjs-2';
// import 'chart.js/auto';

// function Dashboard() {
//   const [summary, setSummary] = useState(null);
//   const [loading, setLoading] = useState(true);

//   useEffect(() => {
//     axios.get('http://localhost:8080/api/summary')
//       .then(res => {
//         setSummary(res.data);
//         setLoading(false);
//       })
//       .catch(err => {
//         console.error("Error fetching summary:", err);
//         setLoading(false);
//       });
//   }, []);

//   if (loading) return <p>Loading dashboard...</p>;

//   return (
//     <div style={{ padding: "2rem" }}>
//       <h1>SumUp Dashboard</h1>

//       <div style={{ display: "flex", gap: "2rem" }}>
//         <div><strong>Total Sales:</strong> €{summary.totalSales}</div>
//         <div><strong>Transactions:</strong> {summary.numTransactions}</div>
//         <div><strong>Average:</strong> €{summary.avgTransactionValue}</div>
//       </div>

//       <div style={{ marginTop: "2rem" }}>
//         <h2>Sales Over Time</h2>
//         <Line
//           data={{
//             labels: summary.salesOverTime.map(pt => pt.date),
//             datasets: [{
//               label: 'Daily Sales (€)',
//               data: summary.salesOverTime.map(pt => pt.amount),
//               fill: false,
//               borderColor: 'blue',
//             }]
//           }}
//         />
//       </div>

//       <div style={{ marginTop: "2rem" }}>
//         <h2>Recent Transactions</h2>
//         <table border="1" cellPadding="8">
//           <thead>
//             <tr>
//               <th>Date</th>
//               <th>Amount (€)</th>
//               <th>Payment Type</th>
//               <th>Status</th>
//             </tr>
//           </thead>
//           <tbody>
//             {summary.transactions.map(tx => (
//               <tr key={tx.id}>
//                 <td>{tx.date}</td>
//                 <td>{tx.amount}</td>
//                 <td>{tx.paymentType}</td>
//                 <td>{tx.status}</td>
//               </tr>
//             ))}
//           </tbody>
//         </table>
//       </div>
//     </div>
//   );
// }

// export default Dashboard;
import React from 'react';

function Dashboard() {
  return <h1>Welcome to the SumUp Dashboard</h1>;
}

export default Dashboard;
