package com.example.groww_1.chart

import android.graphics.Color
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*

@Composable
fun StockLineChart(
    dataPoints: List<Float>,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            LineChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                // Prepare entries
                val entries = dataPoints.mapIndexed { index, value ->
                    Entry(index.toFloat(), value)
                }

                val dataSet = LineDataSet(entries, "Closing Price").apply {
                    color = Color.CYAN
                    setCircleColor(Color.MAGENTA)
                    lineWidth = 2f
                    circleRadius = 4f
                    valueTextColor = Color.TRANSPARENT // Hide value dots
                    setDrawValues(false)
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                }

                data = LineData(dataSet)

                // Chart Background and Grid Styling
                setBackgroundColor(Color.parseColor("#131334")) // Match app dark background
                axisRight.isEnabled = false // Hide right axis

                // X Axis
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    textColor = Color.WHITE
                    setDrawGridLines(false)
                }

                // Y Axis (Left)
                axisLeft.apply {
                    textColor = Color.WHITE
                    setDrawGridLines(true)
                    gridColor = Color.GRAY
                }

                // Legend
                legend.apply {
                    textColor = Color.LTGRAY
                    textSize = 12f
                    form = Legend.LegendForm.LINE
                }

                // Description
                description = Description().apply {
                    text = ""
                }

                invalidate()
            }
        }
    )
}
