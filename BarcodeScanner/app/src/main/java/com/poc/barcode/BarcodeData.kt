package com.poc.barcode

class BarcodeData {
    var barcodeValue: String? = null
    var productName: String? = null
    var price: Double = 0.0

    // Default constructor required for Firebase.
    constructor() {
    }

    constructor(barcodeValue: String) {
        this.barcodeValue = barcodeValue
        this.productName = productName
    }
}

