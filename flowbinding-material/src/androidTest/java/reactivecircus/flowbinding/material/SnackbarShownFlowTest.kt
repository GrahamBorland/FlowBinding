package reactivecircus.flowbinding.material

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.test.filters.LargeTest
import com.google.android.material.snackbar.Snackbar
import org.amshove.kluent.shouldEqual
import org.junit.Test
import reactivecircus.blueprint.testing.asViewAction
import reactivecircus.flowbinding.material.fixtures.MaterialFragment
import reactivecircus.flowbinding.material.test.R
import reactivecircus.flowbinding.testing.FlowRecorder
import reactivecircus.flowbinding.testing.launchTest
import reactivecircus.flowbinding.testing.recordWith

@LargeTest
class SnackbarShownFlowTest {

    @Test
    fun snackbarShownEvents() {
        launchTest<MaterialFragment> {
            val recorder = FlowRecorder<Unit>(testScope)
            val snackbar = Snackbar.make(
                getViewById<CoordinatorLayout>(R.id.rootView),
                "Yo",
                Snackbar.LENGTH_INDEFINITE
            )
            snackbar.shownEvents().recordWith(recorder)

            recorder.assertNoMoreValues()

            snackbar.show().asViewAction()
            recorder.takeValue() shouldEqual Unit

            cancelTestScope()
            recorder.clearValues()

            snackbar.dismiss().asViewAction()
            snackbar.show().asViewAction()
            recorder.assertNoMoreValues()
        }
    }
}