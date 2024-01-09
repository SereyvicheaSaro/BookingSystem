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


    var count = 0;

    document.addEventListener('DOMContentLoaded', function () {
        var addBtn = document.getElementById('addBtn');
        var subtractBtn = document.getElementById('subtractBtn');

        addBtn.addEventListener('click', increaseCount);
        subtractBtn.addEventListener('click', decreaseCount);
    });

    function showBooking(element) {
        var bookingContainer = element.querySelector('.booking-container');
        bookingContainer.style.display = 'flex';
    }

    function hideBooking(element) {
        var bookingContainer = element.querySelector('.booking-container');
        bookingContainer.style.display = 'none';
    }

    function updateQuantity() {
        var input = document.getElementById('quantityInput');
        count = parseInt(input.value) || 1;
        input.value = count;
    }

    function increaseCount() {
        count++;
        updateQuantity();
    }

    function decreaseCount() {
        if (count > 1) {
            count--;
            updateQuantity();
        }
    }

    var quantityInput = document.getElementById("quantityInput");

    function increaseCount() {
        var currentValue = parseInt(quantityInput.value, 10);
        quantityInput.value = currentValue + 1;
    }

    function decreaseCount() {
        var currentValue = parseInt(quantityInput.value, 10);
        if (currentValue > 0) {
            quantityInput.value = currentValue - 1;
        }
    }