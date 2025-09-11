document.getElementById('user-form').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = {
        username: e.target.username.value,
        email: e.target.email.value,
        startDate: new Date().toISOString()
    };

    try {
        const response = await fetch('/api/users', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        });

        if (!response.ok) throw new Error(await response.text());

        const user = await response.json();
        document.getElementById('result').innerHTML =
            `<p>کاربر ایجاد شد:</p>
             <p>ID: ${user.id}</p>
             <p>نام کاربری: ${user.username}</p>
             <p>ایمیل: ${user.email}</p>`;
    } catch (error) {
        alert('خطا در ایجاد کاربر: ' + error.message);
    }
});