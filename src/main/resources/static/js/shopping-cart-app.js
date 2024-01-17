const app = angular.module("shopping-cart-app", []);

app.controller("shopping-cart-ctrl", function ($scope, $http) {
    // alert("AnguularJS initialized");

    //QUẢN LÍ GIỎ HÀNG
    $scope.cart = {
        items: [],

        // validateQuantity(id, quantity) {
        //     $http.get(`/rest/products/${id}`).then(resp => {
        //         const product = resp.data;
        //         if (product && quantity > product.quantity) {
        //             alert("Số lượng vượt quá giới hạn!");
        //             console.error('Số lượng vượt quá giới hạn trong cơ sở dữ liệu.');
        //             // Có thể throw một exception hoặc xử lý lỗi theo ý của bạn
        //         }
        //     }).catch(error => {
        //         console.error('Error fetching product information:', error);
        //     });
        // },

        //Them san pham vao gio hang
        add(id, next) {
            var selectSize = document.getElementById("size");
            var textSize;
            if (selectSize != null && selectSize.value) {
                textSize = selectSize.options[selectSize.selectedIndex].value;
                textSize = Number(textSize);
            }


            var item = this.items.find(item => item.productId == id);

            if (item) {
                item.qty++;
                item.size = textSize;
                this.saveToLocalStorage();
                if (next === 'detail')
                    window.location.href = '/cart/view';
            }
            else {
                $http.get(`/rest/products/${id}`).then(resp => {
                    this.saveToLocalStorage();
                    $http.get(`/rest/products/detail/${id}`).then(respDt => {
                        resp.data.qty = 1;
                        resp.data.size = textSize;
                        resp.data.productsDetail = respDt.data;
                        this.items.push(resp.data);
                        this.saveToLocalStorage();
                        if (next === 'detail')
                            window.location.href = '/cart/view';
                    });
                });
            }
            // alert(id)
        },

        //Xóa sản phẩm khỏi giỏ hàng
        remove(id) {
            var index = this.items.findIndex(item => item.id == id);
            this.items.splice(index, 1);
            this.saveToLocalStorage();
        },

        //Tính tổng số lượng các mặt hàng trong giỏ hàng
        get count() {
            return this.items
                .map(item => item.qty)
                .reduce((total, qty) => total += qty, 0);
        },

        //Tổng thành tiền các mặt hàng trong giỏ
        get amount() {
            return this.items
                .map(item => item.qty * item.price)
                .reduce((total, qty) => total += qty, 0);
        },


        //Luu gio hang vao local storage
        saveToLocalStorage() {
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart", json);
        },

        //Đọc giỏ hàng vào local storage
        loadFromLocalStorage() {
            var json = localStorage.getItem("cart");
            this.items = json ? JSON.parse(json) : [];

        },

        async updateDiscount() {
            try {
                $scope.discount = await this.discount();
                $scope.$apply();
            } catch (error) {
                console.error('Error updating discount:', error);
            }
        },

        watchTextChange() {
            $scope.cart.items.forEach((item, index) => {
                $scope.$watch(`cart.items[${index}].qty`, (newVal, oldVal) => {
                    if (newVal !== oldVal) {
                        this.updateDiscount();
                    }
                });
            });
        },

        async discount() {
            var result = 0;
            var productId = [];
            var products = this.items;

            products.forEach(function (element) {
                productId.push(element.productId);
            });

            console.log(productId);

            try {
                const resp = await $http.get(`/rest/products/discount/${productId}`);
                const discounts = resp.data;

                result = this.items.reduce((total, item) => {
                    const check = discounts.find(discount => discount.products.productId === item.productId);

                    if (check) {
                        const calculatedValue = item.qty * check.quantity;
                        return total + calculatedValue;
                    } else {
                        return total;
                    }
                }, 0);
            } catch (error) {
                console.error(error);
            }
            return result;
        },

        get totalCart() {
            var discount = $('#discountHi').val();
            return this.amount - discount;
        },
        
        clear(){
			this.items = []
			this.saveToLocalStorage();
		}

    }

    $scope.cart.loadFromLocalStorage();
    $scope.cart.updateDiscount();
    $scope.cart.watchTextChange();

    $scope.order = {
        createDate: new Date(),
        address: "",
        accounts: { username: $("#username").text() },
        // order_status: "Chờ xác nhận",
        get ordersDetail() {
            return $scope.cart.items.map(item => {
                return {
                    productsDetail: { products: {productId: item.productId}, pdDetailId: item.productsDetail[0].pdDetailId},
                    price: item.price,
                    quantity: item.qty
                }
            });
        },
        purchase() {
            var order = angular.copy(this);
            //Thực hiện đặt hàng
            $http.post("/rest/orders", order).then(resp => {
                alert("Đặt hàng thành công!");
                $scope.cart.clear();
                location.href = "/order/detail/" + resp.data.orderId;
            }).catch(err => {
                alert("Đặt hàng lỗi!")
                console.log(err);
            })
        }
    }

});
