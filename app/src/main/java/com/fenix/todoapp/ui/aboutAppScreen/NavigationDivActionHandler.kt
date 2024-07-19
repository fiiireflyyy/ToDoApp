package com.fenix.todoapp.ui.aboutAppScreen

import android.content.Context
import android.net.Uri
import com.fenix.todoapp.navigation.NavManager
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class NavigationDivActionHandler(
    private val navManager: NavManager,
) : DivActionHandler() {

    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {

        val url = action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        return if (url.scheme == SCHEME_SAMPLE && handleNavDivAction(url, view.view.context)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }

    }


    private fun handleNavDivAction(action: Uri, context: Context) : Boolean {
        return when(action.host){
            "navigate_to_main" -> {
                navManager.navigateToMainScreen()
                true
            }
            else -> false
        }
    }

    companion object {
        const val SCHEME_SAMPLE = "navigate-action"
    }

}