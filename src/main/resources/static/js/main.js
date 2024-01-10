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
