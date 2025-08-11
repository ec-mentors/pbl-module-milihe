document.addEventListener('DOMContentLoaded', () => {
    const ingredientInput = document.getElementById('ingredient-input');
    const addIngredientBtn = document.getElementById('add-ingredient-btn');
    const ingredientListContainer = document.getElementById('ingredient-list-container');
    const findRecipesBtn = document.getElementById('find-recipes-btn');

    let ingredients = new Set();

    function renderIngredientList() {
        ingredientListContainer.innerHTML = '';
        ingredients.forEach(ingredient => {
            const listItem = document.createElement('li');
            listItem.className = 'ingredient-item';
            listItem.innerHTML = `
                <span>${ingredient}</span>
                <div class="actions">
                    <button class="btn btn-danger">Delete</button>
                </div>
            `;
            listItem.querySelector('.btn-danger').addEventListener('click', () => {
                ingredients.delete(ingredient);
                renderIngredientList();
            });
            ingredientListContainer.appendChild(listItem);
        });
    }

    function addIngredient() {
        const ingredientName = ingredientInput.value.trim().toLowerCase();
        if (ingredientName) {
            ingredients.add(ingredientName);
            ingredientInput.value = '';
            renderIngredientList();
        }
    }

    addIngredientBtn.addEventListener('click', addIngredient);
    ingredientInput.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            addIngredient();
        }
    });

    findRecipesBtn.addEventListener('click', (event) => {
        const ingredientArray = Array.from(ingredients);

        const isVeganOnly = document.getElementById('vegan-filter').checked;

        if (ingredientArray.length === 0) {
            alert("Please add some ingredients first!");
            event.preventDefault();
            return;
        }

        localStorage.setItem('userIngredients', JSON.stringify(ingredientArray));
        localStorage.setItem('veganFilter', isVeganOnly);
    });

    renderIngredientList();
});