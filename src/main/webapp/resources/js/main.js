// $(document).ready()
// {
//     var ctx = document.getElementById('myChart').getContext('2d');
//     var myChart = new Chart(ctx, {
//         // The type of chart we want to create
//         type: 'line',
//         // The data for our dataset
//         data: {
//             labels: [1, 2, 3, 4, 5],
//             datasets: [{
//                 label: 'price',
//                 data: [10, 11, 12, 13, 14],
//                 fill: 'origin',
//                 backgroundColor: 'rgba(255,0,0, 0.4)',
//                 borderColor: 'rgb(255,0,0)',
//                 borderWidth: 1
//             },
//                 {
//                     label: 'price',
//                     data: [2, 1, 12, 2, 1],
//                     fill: 'origin',
//                     backgroundColor: 'rgba(0,255,0, 0.4)',
//                     borderColor: 'rgb(0,255,0)',
//                     borderWidth: 1
//                 }]
//         },
//         // Configuration options go here
//         options: {
//             maintainAspectRatio: false,
//             spanGaps: false,
//             elements: {
//                 line: {
//                     tension: 0.4
//                 }
//             },
//             plugins: {
//                 filler: {
//                     propagate: false
//                 }
//             },
//             scales: {
//                 xAxes: [{
//                     ticks: {
//                         autoSkip: false,
//                         maxRotation: 0
//                     }
//                 }]
//             }
//         }
//     });
//
// }
// ;