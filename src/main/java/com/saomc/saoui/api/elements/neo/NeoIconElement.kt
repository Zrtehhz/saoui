package com.saomc.saoui.api.elements.neo

import com.saomc.saoui.GLCore
import com.saomc.saoui.api.screens.IIcon
import com.saomc.saoui.resources.StringNames
import com.saomc.saoui.util.ColorUtil
import com.teamwizardry.librarianlib.features.animator.Animator
import com.teamwizardry.librarianlib.features.animator.Easing
import com.teamwizardry.librarianlib.features.animator.animations.BasicAnimation
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.kotlin.minus
import com.teamwizardry.librarianlib.features.math.BoundingBox2D
import com.teamwizardry.librarianlib.features.math.Vec2d
import net.minecraft.client.renderer.GlStateManager

/**
 * Part of saoui by Bluexin, released under GNU GPLv3.
 *
 * @author Bluexin
 */
open class NeoIconElement(protected val icon: IIcon, var x: Int = 0, var y: Int = 0) : NeoParent() {

    private var onClickBody: (Vec2d) -> Boolean = { true }
    private var onClickOutBody: (Vec2d) -> Unit = { Unit }

    override val boundingBox = BoundingBox2D(vec(x, y), vec(20 + x, 20 + y))

    override fun draw(mouse: Vec2d, partialTicks: Float) {
        GlStateManager.pushMatrix()
        GLCore.glColorRGBA(if (mouse in this) ColorUtil.HOVER_COLOR else ColorUtil.DEFAULT_COLOR)
        GLCore.glBindTexture(StringNames.gui)
        GLCore.glTexturedRect(x.toDouble(), y.toDouble(), 0.0, 25.0, 20.0, 20.0)
        GLCore.glColorRGBA(if (mouse in this) ColorUtil.HOVER_FONT_COLOR else ColorUtil.DEFAULT_FONT_COLOR)
        if (icon.rl != null) GLCore.glBindTexture(icon.rl!!)
        icon.glDrawUnsafe(x + 2, y + 2)

        GlStateManager.translate(x.toDouble() + childrenXOffset, y.toDouble() + childrenYOffset, 0.0)
        var nmouse = mouse - vec(x + childrenXOffset, y + childrenYOffset)
        elements.filter { it.visible }.forEach {
            it.draw(nmouse, partialTicks)
            GlStateManager.translate(childrenXSeparator.toDouble(), childrenYSeparator.toDouble(), 0.0)
            nmouse -= vec(childrenXSeparator, childrenYSeparator)
        }
        GlStateManager.popMatrix()
    }

    override fun click(pos: Vec2d): Boolean {
        return if (pos in boundingBox) {
            onClickBody(pos)
        } else {
            var npos = pos - vec(x + childrenXOffset, y + childrenYOffset)
            var ok = false
            elements.forEach {
                ok = it.click(npos) || ok
                npos -= vec(childrenXSeparator, childrenYSeparator)
            }
            if (ok) true
            else {
                onClickOutBody(pos)
                false
            }
        }
    }

    override val childrenXOffset = 25
    override val childrenYSeparator = 20

    fun onClick(body: (Vec2d) -> Boolean) {
        onClickBody = body
    }

    fun onClickOut(body: (Vec2d) -> Unit) {
        onClickOutBody = body
    }

    override var visible = true

    override fun hide() {
        visible = false
    }

    override fun show() {
        visible = true
    }
}

class NeoTLCategoryButton(icon: IIcon, x: Int = 0, y: Int = 0) : NeoIconElement(icon, x, y) {

    private var opened = false

    init {
        onClick {
            elements.forEach(if (opened) NeoElement::hide else NeoElement::show)
            opened = !opened

            true
        }

        onClickOut {
            if (opened) elements.forEach(NeoElement::hide)
            opened = false
        }

        val appearAnim = BasicAnimation(this, "y")

        appearAnim.duration = 15f
        appearAnim.from = 0
        appearAnim.repeatCount = 1
        appearAnim.easing = Easing.easeInOutQuint
        Animator().add(appearAnim)
    }

    override fun plusAssign(element: NeoElement) {
        super.plusAssign(element)
        element.hide()
    }

    fun category(icon: IIcon, label: String, body: (NeoCategoryButton.() -> Unit)? = null) {
        val cat = NeoCategoryButton(icon, label)
        if (body != null) cat.body()
        this += cat
    }
}
