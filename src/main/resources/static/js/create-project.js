document.getElementById('project-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = {
        title: e.target.title.value,
        description: e.target.description.value,
        targetAmount: parseFloat(e.target.targetAmount.value),
        endDate: e.target.endDate.value + "T00:00:00",
        userId: parseInt(e.target.userId.value),
        status: "FUNDRAISING",
        startDate: new Date().toISOString()
    };

    try {
        const response = await fetch('/api/projects', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        });

        if (!response.ok) throw new Error(await response.text());

        const project = await response.json();
        document.getElementById('result').innerHTML =
            `<p>پروژه ایجاد شد:</p>
             <p>ID: ${project.id}</p>
             <p>عنوان: ${project.title}</p>
             <p>توضیحات: ${project.description}</p>`;
    } catch (error) {
        alert('خطا در ایجاد پروژه: ' + error.message);
    }
});