$(document).ready(function () {
    // Attach click event to date buttons
    $("button.btn-secondary").click(function () {
      // Remove active class from all buttons
      $("button.btn-secondary").removeClass("active");

      // Add active class to the clicked button
      $(this).addClass("active");

      // Update the hidden input field with the selected date
      var selectedDate = $(this).text();
      $("#selectedDate").val(selectedDate);
    });
  });
  function activateButton(button) {
    // Remove 'active' class from all buttons
    var buttons = document.querySelectorAll('.btn');
    buttons.forEach(function(btn) {
      btn.classList.remove('active');
    });

    // Add 'active' class to the clicked button
    button.classList.add('active');
  }

// Function to increase the quantity
function increaseNumber() {
    var quantity = document.getElementById("quantity");
    quantity.value = parseInt(quantity.value) + 1;
    updatePrice();
}

// Function to decrease the quantity
function decreaseNumber() {
    var quantity = document.getElementById("quantity");
    if (parseInt(quantity.value) > 0) {
        quantity.value = parseInt(quantity.value) - 1;
        updatePrice();
    }
}

// Function to update the price based on quantity
function updatePrice() {
    var quantity = document.getElementById("quantity").value;
    var price = 7.50; // Set your price per ticket here
    document.getElementById("price").innerText = (quantity * price).toFixed(2);
}

// Function to handle booking button click
function bookTicket() {
    var quantity = document.getElementById("quantity").value;
    var price = document.getElementById("price").innerText;
    alert("Booking " + quantity + " ticket(s) for a total price of $" + price);
}