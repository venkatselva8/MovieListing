package app.android.movielisting.utils

import android.content.Context
import android.content.res.Configuration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ResponsiveGridLayoutManager(
    private val context: Context,
    spanCount: Int = 1,
    orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) : GridLayoutManager(context, spanCount, orientation, reverseLayout) {

    init {
        setSpanCountBasedOnOrientation()
    }

    private fun setSpanCountBasedOnOrientation() {
        val orientation = context.resources.configuration.orientation
        spanCount = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            3
        } else {
            7
        }
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        setSpanCountBasedOnOrientation()
        super.onLayoutChildren(recycler, state)
    }
}

