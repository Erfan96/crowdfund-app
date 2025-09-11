async function loadProjects() {
    try {
        const response = await fetch('/api/projects');
        if (!response.ok) throw new Error(await response.text());

        const projects = await response.json();
        const projectList = document.getElementById('project-list');
        projectList.innerHTML = '';

        projects.forEach(project => {
            const li = document.createElement('li');
            li.innerHTML = `<strong>${project.title}</strong> - ${project.description || ''}`;
            li.style.cursor = 'pointer';
            li.onclick = () => loadContributions(project.id);
            projectList.appendChild(li);
        });

    } catch (error) {
        alert('خطا در بارگذاری پروژه‌ها: ' + error.message);
    }
}

async function loadContributions(projectId) {
    try {
        const response = await fetch(`/api/contributions/project/${projectId}/contribution-percentage?status=COMPLETED`);
        if (!response.ok) throw new Error(await response.text());

        const contributions = await response.json();
        const list = document.getElementById('contribution-list');
        list.innerHTML = '';

        contributions.forEach(c => {
            const li = document.createElement('li');
            li.textContent = `کاربر: ${c.user.username} - مبلغ: ${c.amount} - درصد مشارکت: ${c.userContributionPercentage}%`;
            list.appendChild(li);
        });

        document.getElementById('contributions').style.display = 'block';

    } catch (error) {
        alert('خطا در بارگذاری مشارکت‌ها: ' + error.message);
    }
}

loadProjects();
