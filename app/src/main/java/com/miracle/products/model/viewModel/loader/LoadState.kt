package com.miracle.products.model.viewModel.loader

public sealed class LoadState(
    public val initalLoadingCompliete: Boolean
) {

    public class NotLoading(
        initalLoadingCompliete: Boolean
    ) : LoadState(initalLoadingCompliete) {
        override fun toString(): String {
            return "NotLoading(initalLoadingCompliete=$initalLoadingCompliete)"
        }

        override fun equals(other: Any?): Boolean {
            return other is NotLoading &&
                    initalLoadingCompliete == other.initalLoadingCompliete
        }

        override fun hashCode(): Int {
            return initalLoadingCompliete.hashCode()
        }

        internal companion object {
            internal val Complete = NotLoading(initalLoadingCompliete = true)
            internal val Incomplete = NotLoading(initalLoadingCompliete = false)
        }
    }

    public class Loading(
        initalLoadingCompliete: Boolean
    ) : LoadState(initalLoadingCompliete) {
        override fun toString(): String {
            return "Loading(initalLoadingCompliete=$initalLoadingCompliete)"
        }

        override fun equals(other: Any?): Boolean {
            return other is NotLoading &&
                    initalLoadingCompliete == other.initalLoadingCompliete
        }

        override fun hashCode(): Int {
            return initalLoadingCompliete.hashCode()
        }
    }

    public class Error(
        public val error: Throwable,
        initalLoadingCompliete: Boolean
    ) : LoadState(initalLoadingCompliete) {
        override fun equals(other: Any?): Boolean {
            return other is Error &&
                    initalLoadingCompliete == other.initalLoadingCompliete &&
                    error == other.error
        }

        override fun hashCode(): Int {
            return initalLoadingCompliete.hashCode() + error.hashCode()
        }

        override fun toString(): String {
            return "Error(initalLoadingCompliete=$initalLoadingCompliete, error=$error)"
        }
    }

}