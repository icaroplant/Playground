package com.example.playground.ui.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import com.example.playground.R
import com.example.playground.extensions.gone
import com.example.playground.extensions.visible
import kotlinx.android.synthetic.main.animations_fragment.*

class AnimationsFragment : Fragment() {

    private var toogle = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.animations_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        clNotificationItemRoot.setOnClickListener {
            TransitionManager.beginDelayedTransition(clNotificationItemRoot)
            if (toogle) {
                ivNotitificationCenterItemUnseen.gone()
                with(ConstraintSet()) {
                    clone(clNotificationItemRoot)
                    clear(R.id.ivNotitificationCenterItemIcon, ConstraintSet.BOTTOM)
                    applyTo(clNotificationItemRoot)
                }
            } else {
                with(ConstraintSet()) {
                    clone(clNotificationItemRoot)
                    connect(
                        R.id.ivNotitificationCenterItemIcon,
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM
                    )
                    applyTo(clNotificationItemRoot)
                }
                ivNotitificationCenterItemUnseen.visible()
            }
            toogle = !toogle
        }

    }
}