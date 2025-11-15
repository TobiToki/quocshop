document.addEventListener('DOMContentLoaded', function() {
    const closeButton = document.querySelector('.close-search-dropdown');
    const searchForm = document.querySelector('.search-form');

    if (closeButton && searchForm) {
        closeButton.addEventListener('click', function(e) {
            e.preventDefault();
            // Remove focus from all form elements
            document.activeElement.blur();
            // Clear search input
            searchForm.querySelector('input[type="search"]').value = '';
        });
    }

    // Close dropdown when clicking outside
    document.addEventListener('click', function(e) {
        if (!searchForm.contains(e.target)) {
            document.activeElement.blur();
        }
    });
});