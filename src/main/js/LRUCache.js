/*
    LRU Cache
    https://www.codewars.com/kata/53b406e67040e51e17000c0a

Implement a Least Recently Used (LRU) cache. An LRU cache is a key-value store that contains a set capacity for the
number of elements it holds, which is stored in a property. The size should also be a property. When trying to add a new
key-value pair, if cache.size == cache.capacity, the Least Recently Used key is removed.

Hint: You will will need to use the Object.defineProperty facility

Example Behavior:
    var store = new LRUCache(3 // Size of the cache
                            , {a: 1}); // Optional initial values for cache
    store.size; // should be 1
    store.capacity; // should be 3
    store.a; // should be 1;
    store.cache('b', 2)['b']; // should be 2
    store.a = 5;
    store.a; // should be 5
    store.cache('c', 3).cache('d', 4).b; // should be undefined, since 'b' was removed because it was the least recently used
    store.delete('d');
    store.d ; // should be undefined, since 'd' was deleted
    store.size ; // should be 2
    store.cache('c', 4);
    store.c; // should be 4
    store.capacity = 1; // should resize the store to have just one element
    Object.keys(store); // should be ['c']


Full API Specification:
    - The user should be able to make a new cache object with new LRUCache(n), where n is the cache's (initial) capacity
        * The constructor should be able to take a javascript object as an optional second parameter, which will be
          copied and put into the cache
    - Items can be added to the cache using a method called cache
        * cache takes two arguments, a key and a value
            > The new key should be added as a property to the cache
            > The new property should be enumerable
            > It should be possible to reassign the new property
            > Caching an already existing property should update its value
        * The cache method should return the cache itself, so the method can be chained (ie, the builder pattern)
        * The cache method itself should not be enumerable, writable, nor configurable
    - Items can be deleted from the cache using a method called delete
        * This method should not be enumerable, writable, nor configurable
        * This method should have the same return values as the delete builtin
    - The number of elements stored in the cache should be accessible via the size property
        * This property should not be enumerable, writable nor configurable
    - The capacity should be accessible by via the capacity property
        * Making the capacity smaller should trigger the cache to delete the least recently used items until the size of
          the cache is smaller than or equal to the capacity
        * This property should not be enumerable
        * The size property should never acceed the capacity property of a cache
    - An item in the cache is used every time its value is read or assigned, or it is cached using the cache method
*/

function LRUCache(capacity, init) {
  Object.defineProperty(this, '_data', {
    value: {}
  });

  Object.defineProperty(this, '_usages', {
    value: [],
    writable: true
  });

  Object.defineProperty(this, 'size', {
    value: 0,
    writable: true
  });

  Object.defineProperty(this, '_capacity', {
    value: capacity,
    writable: true
  });

  Object.defineProperty(this, 'capacity', {
    get: function() {
    	return this._capacity;
    },
    set: function(val) {
    	this._capacity = val;
      this.removeLRU();
    }
  });

  Object.defineProperty(this, 'delete', {
    value: function(name) {
      if (name === 'delete') return false;
      if (this.hasOwnProperty(name)) {
        this.removeUsage(name);
        this.size--;
      }
      return delete this[name];
    }
  });

  if (init) {
    for (let key in init) {
      this.cache(key, init[key]);
    }
  }
}

LRUCache.prototype.cache = function(name, value) {
	if (!this.hasOwnProperty(name)) {
  	this.size++;
    Object.defineProperty(this, name, {
      configurable: true,
      enumerable: true,
      get: function() {
        this.addUsage(name);
        return this._data[name];
      },
      set: function(val) {
        this.addUsage(name);
        return this._data[name] = val;
      }
    });

    if (this.size > this.capacity) {
      this.removeLRU();
    }
  }
  this[name] = value;
  return this;
}

LRUCache.prototype.removeLRU = function() {
  while (this.size > 0 && this.size > this.capacity) {
    const name = this._usages.shift();
    this.delete(name);
  }
}

LRUCache.prototype.removeUsage = function(name) {
  this._usages = this._usages.filter(v => v !== name);
}

LRUCache.prototype.addUsage = function(name) {
  this.removeUsage(name);
  this._usages.push(name);
}