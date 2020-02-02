package goldzweigapps.com.gencycler

import android.content.Context
import android.os.Build
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import goldzweigapps.com.annotations.annotations.GencyclerModel

abstract class GencyclerListAdapter<T: GencyclerModel, VH: GencyclerHolder>(
    context: Context,
    config: AsyncDifferConfig<T>
): ListAdapter<T, VH>(config) {

    constructor(context: Context, diffCallback: DiffUtil.ItemCallback<T>): this(context, AsyncDifferConfig.Builder(diffCallback).build())

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    /**
     * Helper function to inflate the view holder layout,
     * Can be used by any adapter extending this class
     *
     */
    protected fun inflate(@LayoutRes layoutResId: Int, parent: ViewGroup): View =
        inflater.inflate(layoutResId, parent, false)


    /**
     * @return true if the adapter is empty
     */
    open fun isEmpty(): Boolean = currentList.isEmpty()

    /**
     * @return true if the adapter is not empty
     */
    open fun isNotEmpty(): Boolean = !isEmpty()

    /**
     * Custom '[]' operator
     *
     * allows us the get an item by position
     *
     * @sample yourAdapter[1]
     * @return will return the element at first position
     */
    open operator fun get(position: Int): T = getItem(position)

    /**
     * Custom '[]' operator
     *
     * allows us the get an item by position
     *
     * @sample yourAdapter[element]
     * @return will return the position of the element or -1
     */
    open operator fun get(element: T): Int = currentList.indexOf(element)

    /**
     * Custom 'in' operator
     *
     * allows us to check if an item exist in the adapter
     *
     * @sample element in yourAdapter
     * @return will return if the item exist in the adapter
     */
    open operator fun contains(element: T): Boolean = element in currentList

    /**
     * Checks if the current thread is Ui thread
     *
     * @return true if the current thread is Ui thread
     */
    private fun isUiThread(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Looper.getMainLooper().isCurrentThread
        }
        else {
            Thread.currentThread() == Looper.getMainLooper().thread
        }
    }

}