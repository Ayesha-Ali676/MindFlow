package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun TrendsScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(24.dp)
    ) {
        Text(
            text = "Your Trends",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        StressTrendChart()

        Spacer(modifier = Modifier.height(24.dp))

        HistoryList()
    }
}

@Composable
fun StressTrendChart() {
    val stressData = listOf(3f, 5f, 4f, 7f, 6f, 8f, 5f) // Mock data for 7 days
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "7-Day Stress Level",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            AndroidView(
                factory = { context ->
                    LineChart(context).apply {
                        description.isEnabled = false
                        legend.isEnabled = false
                        setTouchEnabled(true)
                        setPinchZoom(false)
                        setScaleEnabled(false)
                        
                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                            valueFormatter = IndexAxisValueFormatter(days)
                            granularity = 1f
                        }
                        
                        axisLeft.apply {
                            axisMinimum = 0f
                            axisMaximum = 10f
                            setDrawGridLines(true)
                        }
                        
                        axisRight.isEnabled = false
                    }
                },
                update = { chart ->
                    val entries = stressData.mapIndexed { index, value ->
                        Entry(index.toFloat(), value)
                    }
                    
                    val dataSet = LineDataSet(entries, "Stress Score").apply {
                        color = android.graphics.Color.BLUE
                        valueTextSize = 10f
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                        setDrawFilled(true)
                        fillColor = android.graphics.Color.BLUE
                        setCircleColor(android.graphics.Color.BLUE)
                    }
                    
                    chart.data = LineData(dataSet)
                    chart.invalidate()
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun HistoryList() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Recent History", style = MaterialTheme.typography.labelLarge)
        HistoryItem("Oct 24", "High", MaterialTheme.colorScheme.error)
        HistoryItem("Oct 23", "Medium", MaterialTheme.colorScheme.primary)
        HistoryItem("Oct 22", "Low", MaterialTheme.colorScheme.secondary)
    }
}

@Composable
fun HistoryItem(date: String, category: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = date, style = MaterialTheme.typography.bodyLarge)
            Surface(
                color = color,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = category,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
