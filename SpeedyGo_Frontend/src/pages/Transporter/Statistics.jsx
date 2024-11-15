import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie';
import Chart from 'react-apexcharts';
import { useSelector } from 'react-redux';
import Swal from 'sweetalert2';

function Statistics() {
  const [chartData, setChartData] = useState({ series: [], options: {} });
  const email = useSelector(state => state.user.email);
  const token = Cookies.get('token');

  useEffect(() => {
    const fetchEarningsData = async () => {
      try {
        const response = await axios.get(`http://localhost:8091/api/bookings/${email}/earnings`, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });

        const earningsData = response.data;

        // Format the date and time to display them separately in the x-axis
        const formattedDates = earningsData.map(item => {
          const date = new Date(item.dateTime);
          const formattedDate = date.toLocaleDateString();
          const formattedTime = date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
          return `${formattedDate}\n${formattedTime}`; // Combine date and time with newline
        });

        const earnings = earningsData.map(item => parseFloat(item.totalEarnings).toFixed(2)); // Format to 2 decimal points

        setChartData({
          series: [{
            name: 'Total Earnings',
            data: earnings.map(Number) // Convert to numbers for the chart
          }],
          options: {
            chart: {
              type: 'line',
              height: 350,
            },
            colors: ['#008FFB'],
            xaxis: {
              categories: formattedDates, // Use formatted date and time for x-axis
              title: {
                text: 'Date & Time',
              },
              labels: {
                style: {
                  fontSize: '12px',
                  fontWeight: 'bold',
                },
              },
            },
            yaxis: {
              title: {
                text: 'Earnings',
              },
              labels: {
                formatter: (value) => value.toFixed(2),
              },
            },
            title: {
              text: 'Earnings Over Time',
              align: 'left',
            },
            stroke: {
              curve: 'smooth',
            },
          }
        });
      } catch (error) {
        Swal.fire('Error', 'Failed to fetch earnings data', 'error');
      }
    };

    fetchEarningsData();
  }, [email, token]);

  return (
    <div className="statistics-container">
      <h2 className="text-center custom-margin-bottom">Statistics</h2>
      {chartData?.series.length > 0 && <Chart
        options={chartData.options}
        series={chartData.series}
        type="line"
        height={350}
      />}
    </div>
  );
}

export default Statistics;
