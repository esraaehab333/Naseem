package com.example.naseem.presentation.fav.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapSection(
    modifier: Modifier = Modifier,
    mapCenter: GeoPoint?,
    selectedLocation: GeoPoint?
) {
    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(15.0)
            }
        },
        modifier = modifier,
        update = { view ->
            mapCenter?.let { view.controller.animateTo(it) }
            view.overlays.clear()
            selectedLocation?.let { point ->
                val marker = Marker(view).apply {
                    position = point
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "Selected Location"
                }
                view.overlays.add(marker)
            }
            view.invalidate()
        }
    )
}