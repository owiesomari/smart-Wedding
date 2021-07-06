package com.example.smartwedding.models

class Farm {
    var name: String = ""
        get() = field
        set(value) {
            field=(value)
        }

    var location: String = ""
        get() = field
        set(value) {
            field=(value)
        }

    var phone: String = ""
        get() = field
        set(value) {
            field=(value)
        }

    var capacity: Int = 0
        get() = field
        set(value) {
            field=value
        }

    var price: Int = 0
        get() = field
        set(value) {
            field=value
        }

    var childrenAllowed: Boolean = false
        get() = field
        set(value) {
            field=value
        }

    var imageId: String = ""
        get() = field
        set(value) {
            field=(value)
        }
}