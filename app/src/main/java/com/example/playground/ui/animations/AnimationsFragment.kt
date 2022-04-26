package com.example.playground.ui.animations

import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.example.playground.R
import com.example.playground.common.CoreFragment
import com.example.playground.databinding.AnimationsFragmentBinding
import com.example.playground.extensions.gone
import com.example.playground.extensions.visible

class AnimationsFragment :
    CoreFragment<AnimationsFragmentBinding>(AnimationsFragmentBinding::inflate) {

    private var toogle = true

    override fun setupViews() {

        binding.clNotificationItemRoot.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.clNotificationItemRoot)
            if (toogle) {
                binding.ivNotitificationCenterItemUnseen.gone()
                with(ConstraintSet()) {
                    clone(binding.clNotificationItemRoot)
                    clear(R.id.ivNotitificationCenterItemIcon, ConstraintSet.BOTTOM)
                    applyTo(binding.clNotificationItemRoot)
                }
            } else {
                with(ConstraintSet()) {
                    clone(binding.clNotificationItemRoot)
                    connect(
                        R.id.ivNotitificationCenterItemIcon,
                        ConstraintSet.BOTTOM,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.BOTTOM
                    )
                    applyTo(binding.clNotificationItemRoot)
                }
                binding.ivNotitificationCenterItemUnseen.visible()
            }
            toogle = !toogle
        }
    }
}