document.addEventListener('DOMContentLoaded', () => {
    const titleElement = document.getElementById('recipe-title');
    const imageElement = document.getElementById('recipe-image');
    const ingredientListElement = document.getElementById('ingredient-list');
    const instructionsElement = document.getElementById('instructions-content');

    const urlParams = new URLSearchParams(window.location.search);
    const recipeId = urlParams.get('id');

    if (!recipeId) {
        titleElement.textContent = 'Error';
        instructionsElement.innerHTML = '<p>No recipe ID was found in the URL. Please go back and try again.</p>';
        return;
    }

    async function fetchAndDisplayRecipe() {
        try {
            const response = await fetch(`http://127.0.0.1:8080/api/recipes/${recipeId}`);
            if (response.status === 404) { throw new Error('Recipe not found.'); }
            if (!response.ok) { throw new Error(`HTTP error! Status: ${response.status}`); }

            const recipe = await response.json();
            renderRecipeDetails(recipe);
        } catch (error) {
            console.error("Error fetching recipe details:", error);
            titleElement.textContent = 'Error';
            instructionsElement.innerHTML = `<p>Sorry, something went wrong. Error: ${error.message}</p>`;
        }
    }

    function renderRecipeDetails(recipe) {
        document.title = recipe.title;
        titleElement.textContent = recipe.title;
        imageElement.src = recipe.image || 'https://placehold.co/800x300?text=No+Image';
        imageElement.alt = recipe.title;

        ingredientListElement.innerHTML = '';
        recipe.extendedIngredients.forEach(ingredient => {
            const listItem = document.createElement('li');
            listItem.textContent = `${ingredient.amount} ${ingredient.unit} ${ingredient.name}`;
            ingredientListElement.appendChild(listItem);
        });

        if (recipe.stepByStepInstruction) {
            instructionsElement.innerHTML = `<p>${recipe.stepByStepInstruction.replace(/\n/g, '<br>')}</p>`;
        } else {
            instructionsElement.innerHTML = '<p>No instructions available for this recipe.</p>';
        }
    }

    fetchAndDisplayRecipe();
});