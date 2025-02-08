/* Created by: Nicole Phoebe Valentino 12/05/2023 */
/* CMSC 12 Lab (1S 2023-24)B-1L */
/* Javascript */

/* This is the Javascript for the Party Details form. */

// Monetary values for each option
const appetizerPrices = {
    'Salad': 100,
    'Bread w/Dip': 70,
    'Tomato Surprise': 120,
    'Mushroom Bites': 150
};

const mainDishesPrices = {
    'Roast Beef': 300,
    'Beef Steak': 270,
    'Pork Spareribs': 240,
    'Pork Marbella': 250,
    'Grilled Chicken': 190,
    'Roast Chicken': 190,
    'Broiled Salmon': 170,
    'Grilled Salmon': 180
};

const dessertsPrices = {
    'Molten Chocolate Cake': 120,
    'Red Velvet Cake': 90,
    'Lemon Bars': 50,
    'Peanut Butter Bars': 60,
    'Buko Pie': 50,
    'Lemon Meringue Pie': 70
};

const ricePrices = {
    'Plain': 30,
    'Garlic': 40,
    'Bagoong': 35
};

const drinksPrices = {
    'Cucumber Lemonade': 60,
    'Red Iced Tea': 50,
    'Ripe Mango Juice': 70
};

// Function to get selected options for checkboxes
function getSelectedOptions(category) {
    const selectedOptions = document.querySelectorAll(`input[name="${category}"]:checked`);
    const optionsArray = Array.from(selectedOptions).map(option => option.value);
    return optionsArray;
}

function calculateMealCost() {
    const appetizerCost = getSelectedOptionsCost('appetizer', appetizerPrices);
    const mainDishesCost = getSelectedOptionsCost('Main Dishes', mainDishesPrices);
    const dessertsCost = getSelectedOptionsCost('Desserts', dessertsPrices);
    const riceCost = getSelectedOptionsCost('Rice', ricePrices);
    const drinksCost = getSelectedOptionsCost('Drinks', drinksPrices);

    // Sum of all selected options
    const mealCostPerPerson = appetizerCost + mainDishesCost + dessertsCost + riceCost + drinksCost;

    return mealCostPerPerson;
}

function getSelectedOptionsCost(category, prices) {
    const selectedOptions = getSelectedOptions(category);
    let totalCost = 0;

    selectedOptions.forEach(option => {
        totalCost += prices[option] || 0;
    });

    return totalCost;
}

function calculateDeliveryFee(people) {
    if (people <= 50) {
        return 1000;
    } else {
        const additionalFees = Math.ceil((people - 50) / 50) * 500;
        return 1000 + additionalFees;
    }
}

function submitForm() {
    // Get form inputs
    const name = document.getElementById('Name').value;
    const mobile = document.getElementById('number').value;
    const email = document.getElementById('email').value;
    const people = parseInt(document.getElementById('NOP').value);
    const venueAddressInput = document.getElementById('venueAddress');
    const partyDate = document.getElementById('partyDate').value;
    const partyTime = document.getElementById('partyTime').value;
    const deliveryOption = document.querySelector('input[name="deliveryOption"]:checked').value;
    venueAddressInput.disabled = deliveryOption === 'storePickup';

    // Validation for each input field
    if (!isValidName(name)) {
        alert('Name can only contain letters and spaces.');
        return;
    }

    if (!isValidMobile(mobile)) {
        alert('Follow the number format.');
        return;
    }

    if (!isValidEmail(email)) {
        alert('Invalid email format.');
        return;
    }

    if (!isValidPeople(people)) {
        alert('Number of people must be 10 or more and should be a number.');
        return;
    }

    if (!venueAddressInput.value.trim()) {
        alert('Venue Address is required.');
        return;
    }

    if (!partyDate) {
        alert('Party Date is required.');
        return;
    }

    if (!partyTime) {
        alert('Party Time is required.');
        return;
    }

    // Validate Venue Address if delivery is selected
    if (deliveryOption === 'Delivery' && !venueAddressInput.value.trim()) {
        alert('Venue Address is required for delivery.');
        return;
    }

    // Additional validation for party date and time
    const currentDate = new Date();
    const selectedDate = new Date(partyDate + ' ' + partyTime);
    if (selectedDate <= currentDate) {
        alert('Please provide a future date!');
        return;
    }

    const selectedTime = parseInt(partyTime.split(':')[0]);
    if (selectedTime < 6 || selectedTime > 18) {
        alert('Delivery times are only from 6am to 6pm.');
        return;
    }

    // Compute total cost
    const mealCostPerPerson  = calculateMealCost();
    const deliveryFee = calculateDeliveryFee(people);
    const totalCost = mealCostPerPerson  * people + deliveryFee;

    // Get selected options for order summary
    const deliveryOptionText = deliveryOption === 'storePickup' ? 'Store Pickup' : 'Delivery';
    const chosenAppetizer = document.querySelector('input[name="appetizer"]:checked').value;
    const chosenMainDishes = getSelectedOptions('Main Dishes');
    const chosenDesserts = getSelectedOptions('Desserts');
    const chosenRice = document.querySelector('input[name="Rice"]:checked').value;
    const chosenDrinks = document.querySelector('input[name="Drinks"]:checked').value;

    // Display order summary
    const summaryMessage = '▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n' +
    'This is food catering meal plan!\n' +
    '▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n' +
    '\t• Number of People: ' + people + '\n' +
    '▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n' +
    '\t• Appetizer: ' + chosenAppetizer + '\n' +
    '\t• Main Dishes: ' + chosenMainDishes.join(', ') + '\n' +
    '\t• Desserts: ' + chosenDesserts.join(', ') + '\n' +
    '\t• Type of Rice: ' + chosenRice + '\n' +
    '\t• Drinks: ' + chosenDrinks + '\n' +
    '▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n' +
    'Delivery Details:\n' +
    '\t• Delivery Option: ' + deliveryOptionText + '\n' +
    '\t• Venue Address: ' + venueAddressInput.value + '\n' +
    '\t• Selected Date: ' + partyDate + '\n' +
    '\t• Selected Time: ' + partyTime + '\n' +
    '▬▬▬▬▬▬▬▬▬ஜ۩۞۩ஜ▬▬▬▬▬▬▬▬▬\n' +
    '\t• Meal Cost (per person): PHP ' + mealCostPerPerson.toFixed(2) + '\n' +
    '\t• Delivery Fee: PHP ' + deliveryFee.toFixed(2) + '\n' +
    '\t• Total Cost of the Order: PHP ' + totalCost.toFixed(2);

    alert(summaryMessage);
}

// Validation functions
function isValidName(name) {
    return /^[a-zA-Z\s]+$/.test(name);
}

function isValidMobile(mobile) {
    return /^\d{11}$/.test(mobile);
}

function isValidEmail(email) {
    return /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/.test(email);
}

function isValidPeople(people) {
    return /^\d+$/.test(people) && parseInt(people) >= 10;
}

// JavaScript to add and remove the 'scrolled' class based on scroll position
window.addEventListener('scroll', function () {
    var header = document.querySelector('.responsive-header');
    header.classList.toggle('scrolled', window.scrollY > 0);
});

// REFERENCES:
// https://www.geeksforgeeks.org/javascript-const/
// https://www.w3schools.com/jsref/met_document_queryselectorall.asp
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math/ceil
// https://www.w3schools.com/js/js_switch.asp
// https://www.w3schools.com/js/js_dates.asp
// https://www.w3schools.com/js/js_popup.asp
// https://www.youtube.com/watch?v=3iSrABLSwr8
// https://www.youtube.com/watch?v=X4hkueyp7zY
// https://stackoverflow.com/questions/48125405/what-does-mean-in-a-za-za-z-s
// https://stackoverflow.com/questions/26211225/validating-phone-numbers-using-javascript
// https://stackoverflow.com/questions/4640583/what-are-these-javascript-syntax-called-a-za-z0-9-a-za-z0-9
// https://www.w3schools.com/jsref/jsref_regexp_digit_non.asp
// https://www.washington.edu/accesscomputing/webd2/student/unit5/module2/lesson1.html