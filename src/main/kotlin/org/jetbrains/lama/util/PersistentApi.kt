package org.jetbrains.lama.util

typealias IPersistentList<T> = io.lacuna.bifurcan.IList<T>
typealias PersistentList<T> = io.lacuna.bifurcan.List<T>
typealias IPersistentMap<K, V> = io.lacuna.bifurcan.IMap<K, V>
typealias PersistentMap<K, V> = io.lacuna.bifurcan.Map<K, V>
typealias IPersistentSet<T> = io.lacuna.bifurcan.ISet<T>
typealias PersistentSet<T> = io.lacuna.bifurcan.Set<T>

fun <K, V> Map<K, V>.toPersistent(): PersistentMap<K, V> = PersistentMap.from(this)
fun <V> List<V>.toPersistent(): PersistentList<V> = PersistentList.from(this)
fun <V> Set<V>.toPersistent(): PersistentSet<V> = PersistentSet.from(this)

fun IPersistentList<*>.isEmpty(): Boolean = size() == 0L
fun IPersistentList<*>.isNotEmpty(): Boolean = !isEmpty()
