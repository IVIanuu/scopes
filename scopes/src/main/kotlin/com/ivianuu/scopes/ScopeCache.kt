package com.ivianuu.scopes

/**
 * A cache for [Scope]s
 */
class ScopeCache<K>(private val factory: (K) -> Scope) {

    private val scopes = hashMapOf<K, Scope>()

    /**
     * Returns the [Scope] for the given [key]
     */
    fun get(key: K): Scope {
        var scope = synchronized(scopes) { scopes[key] }
        if (scope == null) {
            scope = factory(key)
            scope.onClose {
                synchronized(scopes) {
                    scopes -= key
                }
            }
            synchronized(scopes) { scopes[key] = scope }
        }

        return scope
    }

}