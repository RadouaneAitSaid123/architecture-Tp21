import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { Calendar, User, Bed, Trash2, Edit } from 'lucide-react';
import api from '../services/api';

const ReservationList = () => {
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchReservations();
    }, []);

    const fetchReservations = async () => {
        try {
            const response = await api.get('');
            setReservations(response.data);
        } catch (error) {
            console.error('Error fetching data:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this reservation?')) {
            try {
                await api.delete(`/${id}`);
                fetchReservations(); // Refresh list
            } catch (error) {
                alert('Failed to delete reservation');
            }
        }
    };

    if (loading) return <div className="container" style={{ textAlign: 'center', marginTop: '4rem' }}>Loading...</div>;

    return (
        <div className="container">
            <h2 style={{ fontSize: '1.875rem', fontWeight: 'bold', marginBottom: '2rem' }}>Current Reservations</h2>

            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '2rem' }}>
                {reservations.map((res) => (
                    <div key={res.id} className="card">
                        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'start', marginBottom: '1rem' }}>
                            <div style={{ display: 'flex', alignItems: 'center', gap: '0.75rem' }}>
                                <div style={{ background: '#334155', padding: '0.5rem', borderRadius: '50%' }}>
                                    <User size={20} color="#94a3b8" />
                                </div>
                                <div>
                                    <h3 style={{ margin: 0, fontSize: '1.1rem' }}>{res.client?.nom} {res.client?.prenom}</h3>
                                    <span style={{ fontSize: '0.875rem', color: 'var(--text-muted)' }}>{res.client?.email}</span>
                                </div>
                            </div>
                            <span style={{
                                padding: '0.25rem 0.75rem',
                                borderRadius: '1rem',
                                background: 'rgba(59, 130, 246, 0.2)',
                                color: '#60a5fa',
                                fontSize: '0.75rem',
                                fontWeight: '600'
                            }}>
                                #{res.id}
                            </span>
                        </div>

                        <div style={{ paddingTop: '1rem', borderTop: '1px solid var(--border)', display: 'flex', flexDirection: 'column', gap: '0.75rem' }}>
                            <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', color: 'var(--text-muted)' }}>
                                <Bed size={16} />
                                <span>Room: {res.chambre?.type} (${res.chambre?.prix})</span>
                            </div>
                            <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', color: 'var(--text-muted)' }}>
                                <Calendar size={16} />
                                <span>{res.dateDebut} — {res.dateFin}</span>
                            </div>
                        </div>

                        <div style={{ marginTop: '1.5rem', display: 'flex', gap: '1rem' }}>
                            <Link to={`/edit/${res.id}`} className="btn" style={{ background: '#334155', flex: 1, justifyContent: 'center' }}>
                                <Edit size={16} /> Edit
                            </Link>
                            <button onClick={() => handleDelete(res.id)} className="btn btn-danger" style={{ padding: '0.5rem' }}>
                                <Trash2 size={16} />
                            </button>
                        </div>
                    </div>
                ))}

                {reservations.length === 0 && (
                    <div style={{ gridColumn: '1/-1', textAlign: 'center', padding: '4rem', color: 'var(--text-muted)' }}>
                        No reservations found. Create one to get started.
                    </div>
                )}
            </div>
        </div>
    );
};

export default ReservationList;
