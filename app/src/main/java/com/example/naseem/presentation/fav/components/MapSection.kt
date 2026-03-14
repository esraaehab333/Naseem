package com.example.naseem.presentation.fav.components

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.naseem.R
import com.example.naseem.utils.DrawableHelper
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

@Composable
fun MapSection(
    modifier: Modifier = Modifier,
    mapCenter: GeoPoint?,
    currentLocation: GeoPoint?,
    selectedLocation: GeoPoint?,
    onLocationSelected: (GeoPoint) -> Unit
) {
    val myLocationLabel = stringResource(R.string.my_location)
    val selectedLocationLabel = stringResource(R.string.selected_location)

    AndroidView(
        factory = { ctx ->
            val mapView = MapView(ctx).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(18.0)
                zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            }

            val mapEventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
                override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                    p?.let {
                        mapView.controller.animateTo(it)
                        mapView.controller.setZoom(18.0)
                        onLocationSelected(it)
                    }
                    return true
                }
                override fun longPressHelper(p: GeoPoint?): Boolean = false
            })

            mapView.tag = mapEventsOverlay
            mapView.overlays.add(mapEventsOverlay)
            mapView
        },

        modifier = modifier,

        update = { view ->
            val savedOverlay = view.tag as? MapEventsOverlay
            view.overlays.clear()
            savedOverlay?.let { view.overlays.add(it) }
            if (selectedLocation == null) {
                currentLocation?.let { point ->
                    val marker = Marker(view).apply {
                        position = point
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        title = myLocationLabel
                        icon = DrawableHelper.resizeDrawable(view.context, R.drawable.ic_location_soild, 70, 100, Color.RED)
                    }
                    view.overlays.add(marker)
                }
            }
            selectedLocation?.let { point ->
                val marker = Marker(view).apply {
                    position = point
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = selectedLocationLabel
                    icon = DrawableHelper.resizeDrawable(view.context, R.drawable.ic_location_soild, 70, 100, Color.RED)
                }
                view.overlays.add(marker)
            }
            mapCenter?.let { view.controller.animateTo(it) }
            view.invalidate()
        }
    )
}