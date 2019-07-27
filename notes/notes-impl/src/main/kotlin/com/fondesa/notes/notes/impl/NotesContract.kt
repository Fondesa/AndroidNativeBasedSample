package com.fondesa.notes.notes.impl

import com.fondesa.je.restaurantlist.api.Restaurant
import com.fondesa.je.ui.api.mvp.BasePresenter

/**
 * Contract between the view and the presenter for the restaurant list screen.
 */
object NotesContract {

    /**
     * Defines all the actions the view should perform when the presenter notifies them.
     */
    interface View {

        /**
         * Shows the progress indicator.
         */
        fun showProgressIndicator()

        /**
         * Hides the progress indicator.
         */
        fun hideProgressIndicator()

        /**
         * Shows the container of the restaurants.
         */
        fun showListContainer()

        /**
         * Hides the container of the restaurants.
         */
        fun hideListContainer()

        /**
         * Shows an error to the user for the given cause.
         *
         * @param cause the cause of the error.
         */
        fun showErrorForCause(cause: ErrorCause)

        /**
         * Shows the view which indicates there are no restaurants available for the given postcode.
         */
        fun showZeroElementsView()

        /**
         * Hides the view which indicates there are no restaurants available for the given postcode.
         */
        fun hideZeroElementsView()

        /**
         * Enables the user input in the postcode field.
         */
        fun enablePostcodeInput()

        /**
         * Disables the user input in the postcode field.
         */
        fun disablePostcodeInput()

        /**
         * Enables the user click on the search button.
         */
        fun enableSearchAction()

        /**
         * Disables the user click on the search button.
         */
        fun disableSearchAction()

        /**
         * Updates the current list of restaurants with the given one.
         *
         * @param restaurantList the new list of restaurants which should be shown.
         */
        fun showRestaurantList(restaurantList: List<Restaurant>)

        /**
         * Updates the postcode inside the related input field.
         *
         * @param postcode the new postcode which should be inserted in the input field.
         */
        fun showDefaultPostcode(postcode: String)
    }

    /**
     * Defines all the actions the presenter should perform when the view notifies them.
     */
    interface Presenter : BasePresenter {

        /**
         * Invoked when the user edits the postcode in the input field.
         *
         * @param text the new postcode.
         */
        fun postcodeEdited(text: String)

        /**
         * Invoked when the user clicks on the search button.
         */
        fun searchClicked()
    }

    /**
     * Defines all the causes which can bring to an error.
     */
    enum class ErrorCause {

        /**
         * Identifies an error related to the network.
         */
        NETWORK,

        /**
         * Identifies a generic error.
         */
        GENERIC
    }
}